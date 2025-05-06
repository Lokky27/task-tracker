package ru.holding.srf.userservice.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.holding.srf.userservice.service.UserService;
import ru.srfholding.trackerdto.base.response.TrackerResponse;
import ru.srfholding.trackerdto.users.request.CreateUserRequest;
import ru.srfholding.trackerdto.users.request.UpdateUserRequest;
import ru.srfholding.trackerdto.users.response.UserResult;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<TrackerResponse<UserResult>> getUserById(@RequestHeader("rqUid") String rqUid,
                                                                   @RequestHeader("rqTm") String rqTm,
                                                                   @PathVariable("userId")UUID userId) {

        return ResponseEntity.ok(userService.getUserById(rqUid, rqTm, userId));
    }

    @GetMapping
    public ResponseEntity<TrackerResponse<UserResult>> getUserByEmail(@RequestHeader("rqUid") String rqUid,
                                                                      @RequestHeader("rqTm") String rqTm,
                                                                      @RequestParam("email") String email) {

        return ResponseEntity.ok(userService.getUserByEmail(rqUid, rqTm, email));
    }

    @PostMapping("/create")
    public ResponseEntity<TrackerResponse<UserResult>> createUser(@RequestHeader("rqUid") String rqUid,
                                                                  @RequestHeader("rqTm") String rqTm,
                                                                  @RequestBody CreateUserRequest request) {

        return ResponseEntity.ok(userService.createUser(rqUid, rqTm, request));
    }

    @PatchMapping("/{userId}/update")
    public ResponseEntity<TrackerResponse<UserResult>> updateUserById(@RequestHeader("rqUid") String rqUid,
                                                                      @RequestHeader("rqTm") String rqTm,
                                                                      @PathVariable("userId") String userId,
                                                                      @RequestBody UpdateUserRequest request) {

        return ResponseEntity.ok(userService.updateUser(rqUid, rqTm, UUID.fromString(userId), request));
    }

    @DeleteMapping("/{userId}/delete")
    public ResponseEntity<TrackerResponse<Void>> deleteUser(@RequestHeader("rqUid") String rqUid,
                                                                  @RequestHeader("rqTm") String rqTm,
                                                                  @PathVariable("userId") String userId) {

        return ResponseEntity.ok(userService.deleteUser(rqUid, rqTm, UUID.fromString(userId)));
    }
}
