package me.john.amiscaray.blogapi.services

interface MailService {

    fun sendTemplatedEmail(htmlBody: String, to: String, subject: String)

    fun processTemplate(fileName: String, templateModel: Map<String, Any>): String

}