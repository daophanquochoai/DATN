package doctorhoai.learn.manage_medical.exception.handle;

import doctorhoai.learn.manage_medical.exception.constant.ETypeLog;
import io.sentry.Sentry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class Log {

    // debug by sl4j
    public void logSl4j(final String message, ETypeLog typeLog, Exception ...exception){
        if(typeLog.equals(ETypeLog.ERROR)){
            log.error(message, exception);
        }else if(typeLog.equals(ETypeLog.WARNING)) {
            log.warn(message, exception);
        }else if(typeLog.equals(ETypeLog.INFO)) {
            log.info(message, exception);
        } else if(typeLog.equals(ETypeLog.DEBUG)) {
            log.debug(message, exception);
        }
    }

    // debug by sentry
    public void logSentry(final String message, ETypeLog typeLog, Exception ...exception){
        Sentry.captureMessage(message);
        if( exception != null && exception.length > 0){
            Sentry.captureException(exception[0]);
        }
    }
}
