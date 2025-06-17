package com.example.managesolution.service;

import com.example.managesolution.data.domain.Member;
import com.example.managesolution.mapper.MemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberMapper memberMapper;

    public List<Member> findPaged(int page, int size) {
        int offset = (page - 1) * size;
        return memberMapper.findPaged(size, offset);
    }

    public int countAll() {
        return memberMapper.countAll();
    }
    public Member findById(Long id) {
        return memberMapper.findById(id);
    }

    public void save(Member member) {
        memberMapper.insert(member);
    }

    public void update(Member member) {
        memberMapper.update(member);
    }

    public void delete(Long id) {
        memberMapper.delete(id);
    }
}
