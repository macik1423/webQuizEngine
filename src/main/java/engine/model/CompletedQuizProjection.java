package engine.model;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface CompletedQuizProjection {
    long getId();
    LocalDateTime getCompletedAt();
}
