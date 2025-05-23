package kakao.beno2homework.v2.member.service;

import kakao.beno2homework.common.memberEx.AuthenticationFailedException;
import kakao.beno2homework.common.memberEx.BadRequestMemberException;
import kakao.beno2homework.common.memberEx.NotFoundMemberException;
import kakao.beno2homework.v2.entity.Member;
import kakao.beno2homework.v2.member.dto.*;
import kakao.beno2homework.common.memberEx.DuplicateMemberException;
import kakao.beno2homework.v2.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    public MemberResponseDto join(MemberJoinDto memberJoinDto) {
        if (memberRepository.findByEmail(memberJoinDto.getEmail()).isPresent())
            throw new DuplicateMemberException("이미 회원가입되어진 이메일입니다.");

        return memberRepository.save(memberJoinDto);
    }

    public MemberResponseDto updateMemberName(MemberUpdateNameDto memberUpdateDto) {
        Member validateMember = validateMember(memberUpdateDto.getEmail(), memberUpdateDto.getPassword());

        memberRepository.updateName(memberUpdateDto.getUpdateName(), validateMember.getId());

        Member updatedMember = memberRepository.findById(validateMember.getId())
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        return new MemberResponseDto(updatedMember);
    }

    public MemberResponseDto updatePassword(MemberUpdatePasswordDto memberUpdatePasswordDto) {
        Member validateMember = validateMember(memberUpdatePasswordDto.getEmail(), memberUpdatePasswordDto.getPassword());

        if (!memberUpdatePasswordDto.getChangePassword().equals(memberUpdatePasswordDto.getConfirmPassword()))
            throw new BadRequestMemberException("변경하려는 비밀번호가 일치하지 않습니다.");

        memberRepository.updatePassword(memberUpdatePasswordDto.getChangePassword(), validateMember.getId());

        Member updatedMember = memberRepository.findById(validateMember.getId())
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));

        return new MemberResponseDto(updatedMember);
    }

    public void deleteMember(MemberDeleteDto memberDeleteDto) {
        Member member = validateMember(memberDeleteDto.getEmail(), memberDeleteDto.getPassword());

        memberRepository.delete(member.getId());
    }

    public Member validateMember(String email, String password) {
        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new NotFoundMemberException("존재하지 않는 회원입니다."));
        if (!findMember.getPassword().equals(password)) throw new AuthenticationFailedException("비밀번호가 일치하지 않습니다.");

        return findMember;
    }

}
