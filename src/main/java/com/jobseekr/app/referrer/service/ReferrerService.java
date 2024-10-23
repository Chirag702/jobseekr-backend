package com.jobseekr.app.referrer.service;

import java.util.List;

import com.jobseekr.app.referrer.entity.Referrer;

import jakarta.mail.MessagingException;

public interface ReferrerService {
    Referrer createReferrer(Referrer referrer, String email) throws MessagingException;
    List<Referrer> getAllReferrers();
    Referrer getReferrerById(Long id);
    void deleteReferrer(Long id);
}