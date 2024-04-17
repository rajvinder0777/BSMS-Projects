package in.sr.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import in.sr.entity.Counsellor;

public interface CounsellorRepo extends JpaRepository<Counsellor, Integer> {
	Counsellor findByEmailAndPwd(String email, String pwd);

	Counsellor findByEmail(String email);
}
