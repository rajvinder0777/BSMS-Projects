package in.ashokit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import in.ashokit.entity.User;
import in.ashokit.repository.UserRepository;

@Service
public class UserSerivceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private EmailUtils emailUtils;

	@Override
	public boolean saveUser(User user) {
		User savedUser = userRepo.save(user);

		if (savedUser.getUid() != null) {
			String subject = "Welcome to SR Digitals - Let's Get Started Today! 🚀";
			String body = "<h1>Dear Chaavi!!,\r\n" + "\r\n<h1>"
					+ "<h3>Welcome to the SR Digitals family! 🎉 We're excited to have you on board and can't wait to see all the incredible things you'll accomplish with us.\r\n<h3>"
					+ "\r\n"
					+ "Your registration is now confirmed, and you're officially part of our community of SR Digitals community."
					+ "Thank you for choosing us. We're committed to providing you with the best experience possible.\r\n"
					+ "\r\n" + "Happy exploring!!\r\n" + "\r\n" + "Best regards,\r\n" + "[Your Name]\r\n"
					+ "SR Digitals.";

			emailUtils.sendEmail(user.getEmail(), subject, body);

		}
		return true;
	}

	@Override
	public User getUser(String email, String pwd) {
		return userRepo.findByEmailAndPwd(email, pwd);

	}

}
