package engine.model;

import org.springframework.beans.factory.annotation.Value;

import java.util.List;

public interface QuizSpecifiedProjection {
    long getId();
    String getTitle();
    String getText();
    List<String> getOptions();
}