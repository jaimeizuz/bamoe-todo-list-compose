package org.jaime.tests.todolist.model.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.MappingConstants.ComponentModel;
import org.jaime.tests.todolist.model.UserTaskDto;
import org.jaime.tests.todolist.entities.UserTask;
import org.jaime.tests.todolist.entities.UserTasks;

/**
 * Mapper to map <code><strong>non-null</strong></code> fields on an input {@link Hero} onto a target {@link Hero}.
 */
@Mapper(componentModel = ComponentModel.JAKARTA_CDI, nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface UserTaskMapper {
    UserTaskDto toResource(UserTask userTask);
    UserTask toEntity(UserTaskDto userTaskDto);

    UserTasksDto toResource(UserTasks userTask);
    UserTasks toEntity(UserTasksDto userTaskDto);
}
