package com.social.conversation.auditor;

import com.social.log.Logger;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuditorAwareImpl implements AuditorAware<String> {

    private final Logger logger;

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.ofNullable(logger.getUserName()).or(() -> Optional.of("system"));
    }
}
