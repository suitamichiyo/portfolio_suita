package com.karainnovate.inimu.service;

import com.karainnovate.inimu.model.Reservation;
import com.karainnovate.inimu.model.WorkshopSlot;
import com.karainnovate.inimu.model.Contact;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender mailSender;

    @Value("${app.mail.dev-mode:true}")
    private boolean devMode;

    @Value("${app.mail.from}")
    private String fromAddress;

    @Value("${app.mail.admin}")
    private String adminAddress;

    public void sendReservationConfirmation(Reservation reservation, WorkshopSlot slot) {
        String subject = "【inimu】ワークショップご予約確認";
        String body = String.format(
            "%s 様\n\n" +
            "inimuワークショップのご予約を承りました。\n\n" +
            "■ご予約内容\n" +
            "日程：%s\n" +
            "時間：%s〜%s\n" +
            "人数：%d名\n" +
            "料金：¥%,d（税込）\n\n" +
            "■キャンセルについて\n" +
            "前日19:00まで無料でキャンセルできます。\n\n" +
            "■アクセス\n" +
            "東京都台東区浅草2丁目1-5\n" +
            "各線浅草駅より徒歩6分 / 浅草寺より徒歩3分\n" +
            "営業時間：10:30〜18:00（火〜日）\n\n" +
            "当日お越しをお待ちしております。\n\n" +
            "inimu / 株式会社 KARA INNOVATE",
            reservation.getName(),
            slot.getSlotDate(),
            slot.getStartTime(),
            slot.getEndTime(),
            reservation.getNumPeople(),
            reservation.getNumPeople() * 5500
        );
        sendMail(reservation.getEmail(), subject, body);
    }

    public void sendReservationNotification(Reservation reservation, WorkshopSlot slot) {
        String subject = "【inimu管理】新規予約が入りました";
        String body = String.format(
            "新規予約通知\n\n" +
            "日程：%s %s〜%s\n" +
            "氏名：%s（%s）\n" +
            "メール：%s\n" +
            "電話：%s\n" +
            "人数：%d名\n" +
            "備考：%s",
            slot.getSlotDate(), slot.getStartTime(), slot.getEndTime(),
            reservation.getName(), reservation.getNameKana(),
            reservation.getEmail(), reservation.getPhone(),
            reservation.getNumPeople(),
            reservation.getAllergyNote() != null ? reservation.getAllergyNote() : "なし"
        );
        sendMail(adminAddress, subject, body);
    }

    public void sendContactAutoReply(Contact contact) {
        String subject = "【inimu】お問い合わせを受け付けました";
        String body = String.format(
            "%s 様\n\n" +
            "お問い合わせありがとうございます。\n" +
            "内容を確認次第、3営業日（火〜日）以内にご連絡いたします。\n\n" +
            "■受付内容\n%s\n\n" +
            "inimu / 株式会社 KARA INNOVATE",
            contact.getName(),
            contact.getMessage()
        );
        sendMail(contact.getEmail(), subject, body);
    }

    private void sendMail(String to, String subject, String body) {
        if (devMode) {
            log.info("=== [DEV MODE] メール送信スキップ ===");
            log.info("To: {}", to);
            log.info("Subject: {}", subject);
            log.info("Body:\n{}", body);
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(fromAddress);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);
        mailSender.send(message);
        log.info("メール送信完了: {}", to);
    }
}
