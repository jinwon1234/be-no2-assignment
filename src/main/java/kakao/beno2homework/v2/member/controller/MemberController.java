package kakao.beno2homework.v2.member.controller;

import jakarta.validation.Valid;
import kakao.beno2homework.v2.member.dto.*;
import kakao.beno2homework.v2.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v2")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/users")
    public ResponseEntity<MemberResponseDto> signup(@Valid @RequestBody MemberJoinDto memberJoinDto) {
        MemberResponseDto response = memberService.join(memberJoinDto);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/users/name")
    public ResponseEntity<MemberResponseDto> updateMember(@Valid @RequestBody MemberUpdateNameDto memberUpdateNameDto) {
        MemberResponseDto memberResponseDto = memberService.updateMemberName(memberUpdateNameDto);

        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @PatchMapping("/users/password")
    public ResponseEntity<MemberResponseDto> updateMemberPassword(@Valid @RequestBody MemberUpdatePasswordDto memberUpdatePasswordDto) {
        MemberResponseDto memberResponseDto = memberService.updatePassword(memberUpdatePasswordDto);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }

    @DeleteMapping("/users")
    public ResponseEntity<Void> deleteMember(@Valid @RequestBody MemberDeleteDto memberDeleteDto) {
        memberService.deleteMember(memberDeleteDto);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
