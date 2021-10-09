package me.john.amiscaray.blogapi.services

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Service
import org.thymeleaf.context.Context
import org.thymeleaf.spring5.SpringTemplateEngine

@Service
class ThymeLeafMailService(private val mailSender: JavaMailSender,
                           @Qualifier("thymeLeafTemplateEngine") private val templateEngine: SpringTemplateEngine): MailService {

    @Value("\${app.mail.from}")
    private lateinit var from: String

    @Async
    override fun sendTemplatedEmail(htmlBody: String, to: String, subject: String) {
        val message = mailSender.createMimeMessage()
        val helper = MimeMessageHelper(message, true, "UTF-8")
        helper.setTo(to)
        helper.setSubject(subject)
        helper.setFrom(from)
        helper.setText(htmlBody, true)
        mailSender.send(message)
    }

    override fun processTemplate(fileName: String, templateModel: Map<String, Any>): String {
        val thymeleafContext = Context()
        thymeleafContext.setVariables(templateModel)
        return templateEngine.process(fileName, thymeleafContext)
    }

}