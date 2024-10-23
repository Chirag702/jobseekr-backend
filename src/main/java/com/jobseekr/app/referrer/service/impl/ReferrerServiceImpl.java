package com.jobseekr.app.referrer.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.jobseekr.app.auth.models.User;
import com.jobseekr.app.auth.repository.UserRepository;
import com.jobseekr.app.email.EmailService;
import com.jobseekr.app.referrer.entity.Referrer;
import com.jobseekr.app.referrer.repository.ReferrerRepository;
import com.jobseekr.app.referrer.service.ReferrerService;

import jakarta.mail.MessagingException;

import java.util.List;

@Service
public class ReferrerServiceImpl implements ReferrerService {

	@Autowired
	private EmailService emailService;
	
	@Autowired
	private UserRepository userRepository;
	
    @Autowired
    private ReferrerRepository referrerRepository;

    @Override
    public Referrer createReferrer(Referrer referrer, String email) throws MessagingException {
        // Retrieve the user by email
        User user = userRepository.findByEmail(email);
        
        // Check if user exists to avoid NullPointerException
        if (user == null) {
            throw new IllegalArgumentException("User not found with email: " + email);
        }
        
        // Set personal email for the referrer
        referrer.setPersonalEmail(user.getEmail());
        
        // Save the referrer
        Referrer savedReferrer = referrerRepository.save(referrer);
        
       user.setIsReferrerFormSubmitted(true);
       userRepository.save(user);
        
        // Prepare the email content
        String emailContent = 
            "<!DOCTYPE html>" +
            "<html lang=\"en\">" +
            "<head>" +
            "    <meta charset=\"UTF-8\">" +
            "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" +
            "    <title>Welcome to the OneFactor referrer program: Instructions for Referrers</title>" +
            "    <style>" +
            "        body { font-family: Arial, sans-serif; margin: 0; padding: 20px; background-color: #f4f4f4; }" +
            "        .container { max-width: 600px; margin: auto; background: white; padding: 20px; border-radius: 5px; box-shadow: 0 0 10px rgba(0, 0, 0, 0.1); }" +
            "        h1 { color: #333; }" +
            "        p { line-height: 1.5; color: #666; }" +
            "        .footer { margin-top: 20px; font-size: 12px; text-align: center; color: #999; }" +
            "    </style>" +
            "</head>" +
            "<body>" +
            "    <div class=\"container\">" +
            "        <h1>Welcome to the OneFactor referral program!</h1>" +
            "        <p>Dear " + user.getFname() + ",</p>" + // Assuming Referrer has a getName() method
            "        <p>Thank you for joining our referral program! Here are the instructions to get started:</p>" +
            "        <ul>" +
            "            <li>Register your account on the portal.</li>" +
            "            <li>Refer candidates by filling out the referral form.</li>" +
            "            <li>Stay updated on the status of your referrals.</li>" +
            "        </ul>" +
            "        <p>If you have any questions, feel free to reach out to us.</p>" +
            "        <p>Best regards,<br>Chirag Agarwal<br>OneFactor</p>" +
            "        <div class=\"footer\">" +
            "            <p>&copy; 2024 Your Company. All rights reserved.</p>" +
            "        </div>" +
            "    </div>" +
            "</body>" +
            "</html>";

        // Send the email
        emailService.sendHtmlEmail(referrer.getPersonalEmail(), 
                                    "Welcome to the OneFactor referrer program: Instructions for Referrers", 
                                    emailContent);
        
        return savedReferrer;
    }


    @Override
    public List<Referrer> getAllReferrers() {
        return referrerRepository.findAll();
    }

    @Override
    public Referrer getReferrerById(Long id) {
        return referrerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Referrer not found with id: " + id));
    }

    @Override
    public void deleteReferrer(Long id) {
        referrerRepository.deleteById(id);
    }
}
