package com.kgb;

import com.kgb.threading.timeline.Timeline;
import com.kgb.threading.timeline.TimelineRepo;
import io.reactivex.Observable;

/**
 * @author Krzysztof Betlej <labiod@wp.pl>
 *         Date: 5/15/17.
 */
public class Main {

    public static final String HELLO_TL_USER = "Hello TL user";

    public static void main(String[] args) {
        Observable<String> observer = Observable.just(HELLO_TL_USER);
        observer.subscribe(System.out::println);
        TimelineRepo.INSTANACE.createTimelineFromName(Timeline.AVAILABLE_TIMELINES_NAMES[0]);
    }
}
