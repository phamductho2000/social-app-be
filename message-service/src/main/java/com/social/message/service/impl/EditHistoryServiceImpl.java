package com.social.message.service.impl;

import com.social.message.domain.EditHistory;
import com.social.message.domain.MessageHistory;
import com.social.message.repo.EditHistoryRepository;
import com.social.message.service.EditHistoryService;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class EditHistoryServiceImpl implements EditHistoryService {

    private final EditHistoryRepository editHistoryRepository;

    private final ModelMapper modelMapper;

    @Override
    public void save(MessageHistory request, String refId) {
        if (Objects.nonNull(request) && StringUtils.hasText(refId)) {
            EditHistory editHistory = modelMapper.map(request, EditHistory.class);
            editHistory.setRefId(refId);
            editHistoryRepository.save(editHistory);
        }
    }
}
