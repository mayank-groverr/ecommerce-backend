package practice.mayank.ecommerce.controller.management;

import com.github.fge.jsonpatch.JsonPatch;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import practice.mayank.ecommerce.dto.user.CreateAdminRequest;
import practice.mayank.ecommerce.dto.user.ManagementUserResponse;
import practice.mayank.ecommerce.dto.user.PasswordUpdateRequest;
import practice.mayank.ecommerce.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/management/admin")
public class AdminController {


    private final UserService userService;

    @PostMapping("/create")
    public ResponseEntity<ManagementUserResponse> createNewAdmin(
            @Valid @RequestBody CreateAdminRequest createAdminRequest
    ){
        ManagementUserResponse newAdmin = userService.createAdminAccount(createAdminRequest);
        return new ResponseEntity<>(newAdmin, HttpStatus.CREATED);
    }

    @GetMapping("/get")
    public ResponseEntity<ManagementUserResponse> getDetail() {
        ManagementUserResponse managementUserResponse = userService.getManagementUser();
        return ResponseEntity.ok(managementUserResponse);
    }

    @PatchMapping(value = "/update",  consumes = "application/json-patch+json")
    public ResponseEntity<ManagementUserResponse> updateUser(@RequestBody JsonPatch jsonPatch) {
        ManagementUserResponse managementUserResponse = userService.updateManagementUser(jsonPatch);
        return ResponseEntity.ok(managementUserResponse);
    }

    @PatchMapping("/update-password")
    public ResponseEntity<String> updateUserPassword(@Valid @RequestBody PasswordUpdateRequest passwordUpdateRequest){
        userService.updateUserPassword(passwordUpdateRequest);
        return new ResponseEntity<>("Password Updated Successfully", HttpStatus.OK);
    }

}
