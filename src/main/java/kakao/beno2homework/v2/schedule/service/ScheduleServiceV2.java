package kakao.beno2homework.v2.schedule.service;

import kakao.beno2homework.common.memberEx.AuthenticationFailedException;
import kakao.beno2homework.common.scheduleEx.NotFoundScheduleException;
import kakao.beno2homework.v2.entity.Member;
import kakao.beno2homework.v2.entity.Schedule;
import kakao.beno2homework.common.memberEx.NotFoundMemberException;
import kakao.beno2homework.v2.member.repository.MemberRepository;
import kakao.beno2homework.v2.member.service.MemberService;
import kakao.beno2homework.v2.schedule.dto.ScheduleDeleteReqDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleRequestDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleResponseDto;
import kakao.beno2homework.v2.schedule.dto.ScheduleUpdateReqDto;
import kakao.beno2homework.v2.schedule.repository.ScheduleRepositoryV2;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class ScheduleServiceV2 {

    private final ScheduleRepositoryV2 scheduleRepository;
    private final MemberService memberService;
    private final MemberRepository memberRepository;


    public ScheduleResponseDto save(ScheduleRequestDto scheduleRequestDto) {

        String email = scheduleRequestDto.getEmail();
        String password = scheduleRequestDto.getPassword();
        Member validatedMember = memberService.validateMember(email, password);

        Schedule save = scheduleRepository.save(scheduleRequestDto, validatedMember);

        return new ScheduleResponseDto(save, validatedMember.getName());
    }

    public ScheduleResponseDto findSchedule(Long id) {
        return scheduleRepository.findByIdWithMemberName(id)
                .orElseThrow(() -> new NotFoundScheduleException("존재하지 않는 일정입니다."));
    }

    public List<ScheduleResponseDto> findMemberSchedules(Long memberId) {

        Member findMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new NotFoundMemberException("존재하지않는 회원입니다."));

        return scheduleRepository.findByMemberId(memberId)
                .stream().map(s-> new ScheduleResponseDto(s, findMember.getName())).toList();
    }

    public List<ScheduleResponseDto> findSchedulesWithPage(String author, LocalDate updateDate, Pageable pageable) {

        List<Member> members = memberRepository.findByName(author);

        if (members.isEmpty()) return List.of();

        List<Long> memberIdList = members
                .stream().map(Member::getId).toList();


        return scheduleRepository.findSchedules(memberIdList, updateDate, pageable);
    }

    public ScheduleResponseDto updateSchedule(Long id, ScheduleUpdateReqDto scheduleUpdateReqDto) {
        String email = scheduleUpdateReqDto.getEmail();
        String password = scheduleUpdateReqDto.getPassword();

        Member member = memberService.validateMember(email, password);

        validateScheduleCRUD(id, member);

        Schedule schedule = scheduleRepository.updateSchedule(id, scheduleUpdateReqDto);

        return new ScheduleResponseDto(schedule, member.getName());
    }



    public void deleteSchedule(Long id, ScheduleDeleteReqDto scheduleDeleteReqDto) {
        String email = scheduleDeleteReqDto.getEmail();
        String password = scheduleDeleteReqDto.getPassword();

        Member member = memberService.validateMember(email, password);

        validateScheduleCRUD(id, member);

        scheduleRepository.delete(id);
    }

    private void validateScheduleCRUD(Long id, Member member) { // 자신의 게시글인지 확인
        Schedule findSchedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new NotFoundScheduleException("존재하지 않는 일정입니다."));

        if (!findSchedule.getMemberId().equals(member.getId()))
            throw new AuthenticationFailedException("자신의 일정이 아닙니다.");
    }
}
