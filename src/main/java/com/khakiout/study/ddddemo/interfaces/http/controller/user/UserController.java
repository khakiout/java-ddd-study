package com.khakiout.study.ddddemo.interfaces.http.controller.user;

import com.khakiout.study.ddddemo.app.user.UserApplication;
import com.khakiout.study.ddddemo.app.user.UserDTO;
import com.khakiout.study.ddddemo.interfaces.http.controller.BaseController;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("users")
public class UserController implements BaseController<UserDTO> {

    @Autowired
    private final UserApplication userApplication;

    public UserController(UserApplication userApplication) {
        this.userApplication = userApplication;
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> get(@PathVariable String id) {
        UserDTO userDTO = userApplication.findById(id);

        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("")
    public ResponseEntity<List<UserDTO>> get() {
        return ResponseEntity.ok(userApplication.getAll());
    }

    @Override
    public ResponseEntity create(UserDTO userDTO) {
        return null;
    }

    @Override
    public ResponseEntity update(UserDTO userDTO) {
        return null;
    }

    @Override
    public ResponseEntity delete(String id) {
        return null;
    }

}
