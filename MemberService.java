// src/service/MemberService.java
package service;

import dao.MemberDAO;
import model.Member;
import java.util.List;

public class MemberService {

    private MemberDAO memberDAO;

    public MemberService() {
        this.memberDAO = new MemberDAO();
    }

    public boolean addMember(Member member) {
        return memberDAO.addMember(member);
    }

    public List<Member> getAllMembers() {
        return memberDAO.getAllMembers();
    }

    public boolean deleteMember(String id) {
        return memberDAO.deleteMember(id);
    }

    public String generateId() {
        return memberDAO.generateId();
    }
}
