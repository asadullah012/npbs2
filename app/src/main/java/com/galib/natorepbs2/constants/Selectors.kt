package com.galib.natorepbs2.constants

object Selectors {
    const val COMPLAIN_CENTRE = "#left > div.news.details > div:nth-child(3) > div > div.html_text.body > div.value > table"
    const val ACHIEVEMENTS = "#left > div.page.details > div > div > div.html_text.body > div > table"
    const val OFFICERS_LIST = "#left > div.officerlist.list > div.table-responsive.chbappy1 > table"
    const val JUNIOR_OFFICER_LIST = "#left > div.page.details > div > div > div.html_text.body > div > table"
    const val BOARD_MEMBER = "#left > div.page.details > div > div > div.html_text.body > div > table"
    const val POWER_OUTAGE_CONTACT = "#left > div.news.details > div:nth-child(3) > div > div.html_text.body > div.value > div:nth-child(3) > table"
    const val POWER_OUTAGE_CONTACT_HEADER = "#left > div.news.details > div:nth-child(4) > div > div.html_text.body > div.value > div:nth-child(1) > span"
    const val POWER_OUTAGE_CONTACT_FOOTER = "#left > div.news.details > div:nth-child(4) > div > div.html_text.body > div.value > div:nth-child(5) > p > span"
    const val TARIFF = "#left > div.page.details > div > div > div.html_text.body > div"
    const val LAST_UPDATE_TIME = "#footer-menu > div > span"
    const val AT_A_GLANCE_MONTH = "#left > div.page.details > div > div > div.html_text.body > div > p:nth-child(2)"
    const val AT_A_GLANCE = "#left > div.page.details > div > div > div.html_text.body > div > table"
    val OFFICES = mapOf( "name" to "#left > div.page.details > div > div > div.html_text.body > div > div > p:nth-child(1) > span",
                         "address" to "#left > div.page.details > div > div > div.html_text.body > div > div > p:nth-child(2) > span",
                         "mobile" to "#left > div.page.details > div > div > div.html_text.body > div > div > p:nth-child(4) > span",
                         "email" to "#left > div.page.details > div > div > div.html_text.body > div > div > p:nth-child(5) > span > span > a",
                         "gMapURL" to "#left > div.page.details > div > div > div.html_text.body > div > div > p:nth-child(6) > span > span > a")
    const val TENDER = "#left > div.revenuemodeltender.list > div.table-responsive.chbappy1 > table"
    const val NOTICE = "#left > div.notices.list > div.table-responsive.chbappy1 > table"
    const val NEWS = "#left > div.news.list > div.table-responsive.chbappy1 > table"
    const val JOB = "#left > div.page.details > div > div > div.html_text.body > div > table"
    const val ELECTRICITY_CONNECTION_RULES = "#left > div.page.details > div > div > div.html_text.body"
    const val SERVICE_LIST = "#left > div.officeprocessmap.details > div:nth-child(3) > div > div.text.body > div.value > div"
    const val COMMUNICATION_POST = "#left > div.page.details > div > div > div.html_text.body"
    const val BANNERS = "#left > div.topbanner.list > div.table-responsive.chbappy1 > table"
}