package com.ley.innovation.contest.configuration;


import com.ley.innovation.contest.business.entity.Event;
import com.ley.innovation.contest.business.entity.Information;
import com.ley.innovation.contest.business.page.EventPage;
import com.ley.innovation.contest.business.page.InformationPage;
import com.ley.innovation.contest.business.service.EventService;
import com.ley.innovation.contest.business.service.InformationService;
import com.ley.innovation.contest.utils.ReadCountUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.List;

@Configuration
@EnableScheduling
@Slf4j
public class ScheduleConfiguration {

    @Autowired
    private EventService eventService;

    @Autowired
    private InformationService informationService;

    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setThreadNamePrefix("read-count-schedule-thread-");
        taskScheduler.setPoolSize(Runtime.getRuntime().availableProcessors());
        return taskScheduler;
    }


    @Scheduled(cron = "* 0/30 * * * ? ")
    public void eventReadCountSchedule() throws Exception {
        EventPage page = new EventPage();
        page.setPageSize(50);
        page.setPage(1);
        //赛事启用
        page.setEventEnable("1");
        //赛事发布<=当前时间
        page.setEventReleaseTime2(DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
        List<Event> enabledEvents = eventService.queryByList(page);
        if (!CollectionUtils.isEmpty(enabledEvents)) {
            for (Event event : enabledEvents) {
                String eventId = event.getEventId();
                if (StringUtils.hasText(eventId)) {
                    Event updateEvent = new Event();
                    updateEvent.setEventId(eventId);
                    if (ReadCountUtils.containsKey(eventId)) {
                        updateEvent.setEventReadCount(ReadCountUtils.get(eventId));
                        eventService.updateByPrimaryKeySelective(updateEvent);
                        log.info("update eventId: {},readCount: {}", eventId, ReadCountUtils.get(eventId));
                    }
                }
            }
        }
    }


    @Scheduled(cron = "* 0/30 * * * ? ")
    public void infoReadCountSchedule() throws Exception {
        InformationPage page = new InformationPage();
        page.setPageSize(50);
        page.setPage(1);
        page.setInfoReleaseTime2(DateFormatUtils.format(new Date(System.currentTimeMillis()), "yyyy-MM-dd HH:mm:ss"));
        List<Information> informations = informationService.queryByList(page);
        if (!CollectionUtils.isEmpty(informations)) {
            for (Information information : informations) {
                String informationId = information.getInfoId();
                if (StringUtils.hasText(informationId)) {
                    Information updateInfo = new Information();
                    updateInfo.setInfoId(informationId);
                    if (ReadCountUtils.containsKey(informationId)) {
                        updateInfo.setInfoReadCount(ReadCountUtils.get(informationId));
                        informationService.updateByPrimaryKeySelective(updateInfo);
                        log.info("update informationId: {},readCount: {}", informationId, ReadCountUtils.get(informationId));
                    }
                }
            }
        }
    }
}
