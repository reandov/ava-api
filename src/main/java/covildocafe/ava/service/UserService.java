package covildocafe.ava.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import covildocafe.ava.model.dto.user.UserDTO;
import covildocafe.ava.model.dto.user.UserResponseDTO;
import covildocafe.ava.model.dto.user.UserUpdateDTO;
import covildocafe.ava.model.entity.User;
import covildocafe.ava.repository.UserRepository;
import covildocafe.ava.utils.ServiceUtils;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Page<UserResponseDTO> list(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<User> usersPage = userRepository.findAll(pageable);

        return usersPage.map(user -> new UserResponseDTO(user.getId(), user.getName(), user.getEmail()));
    }

    public UserResponseDTO create(UserDTO userDTO) {
        if (userRepository.existsByEmail(userDTO.getEmail())) {
            throw new IllegalStateException("Email already exists");
        }

        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());

        User savedUser = userRepository.save(user);

        return new UserResponseDTO(savedUser.getId(), savedUser.getName(), savedUser.getEmail());
    }

    public UserResponseDTO update(Long id, UserUpdateDTO userUpdateDTO) {
        User user = ServiceUtils.findEntityOrThrow(userRepository.findById(id), id, "User");

        if (userUpdateDTO.getName() != null && !userUpdateDTO.getName().isEmpty()) {
            user.setName(userUpdateDTO.getName());
        }

        if (userUpdateDTO.getEmail() != null && !userUpdateDTO.getEmail().isEmpty()) {
            if (userRepository.existsByEmail(userUpdateDTO.getEmail())) {
                throw new IllegalStateException("Email already exists");
            }

            user.setEmail(userUpdateDTO.getEmail());
        }

        if (userUpdateDTO.getPassword() != null && !userUpdateDTO.getPassword().isEmpty()) {
            user.setPassword(userUpdateDTO.getPassword());
        }

        User updatedUser = userRepository.save(user);

        return new UserResponseDTO(updatedUser.getId(), updatedUser.getName(), updatedUser.getEmail());
    }

    public void deleteUser(Long id) {
        ServiceUtils.findEntityOrThrow(userRepository.findById(id), id, "User");

        userRepository.deleteById(id);
    }
}
