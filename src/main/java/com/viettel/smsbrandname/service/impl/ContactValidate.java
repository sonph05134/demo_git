package com.viettel.smsbrandname.service.impl;

import com.viettel.smsbrandname.commons.DataUtil;
import com.viettel.smsbrandname.commons.Translator;
import com.viettel.smsbrandname.config.Constants;
import com.viettel.smsbrandname.domain.CpGroupSub;
import com.viettel.smsbrandname.repository.CpGroupSubRepository;
import com.viettel.smsbrandname.service.dto.CpGroupSubDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

@Component
public class ContactValidate {
    @Autowired
    private CpGroupSubRepository cpGroupSubRepository;

    private static final Logger logger = LoggerFactory.getLogger(ContactValidate.class);


    private static final SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

    public void validateForm(CpGroupSubDTO dto, List<String> lstPrefix, String cpCode, Instant now, Set<String> msisdn) {
        String sexMale = Translator.toLocale("cpGroupSub.sex.male");
        String sexFemale = Translator.toLocale("cpGroupSub.sex.female");
        String sexFemaleNoSign = Translator.toLocale("cpGroupSub.sex.femaleNosign");

        if (DataUtil.isNullOrEmpty(dto.getMsisdn())) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.msisdnNull") + ",");
        } else if(!Pattern.matches("[0-9]+", dto.getMsisdn())) {
            dto.setError(dto.getError() + Translator.toLocale("error.cpGroupSub.msisdn.invalid") + ",");
        } else if (!DataUtil.isValidMsisdn(dto.getMsisdn(), lstPrefix)) {
            dto.setError(dto.getError() + Translator.toLocale("error.cpGroupSub.msisdn.invalid") + ",");
        } else if(!DataUtil.isNullOrEmpty(dto.getMsisdn()) && msisdn.contains(dto.getMsisdn())) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.msisdnExisted") + ",");
        }


        List<CpGroupSub> numObj = cpGroupSubRepository.findMsisdn(dto.getMsisdn(), dto.getId(), dto.getCpGroupId());
        if (!DataUtil.isNullOrEmpty(numObj)) {
            dto.setId(numObj.get(0).getId());
            dto.setUpdateDate(now);
            dto.setCreateDate(numObj.get(0).getCreateDate());
        } else {
            dto.setCreateDate(now);
        }

        if (!DataUtil.isNullOrEmpty(dto.getName()) && dto.getName().length() > 255) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.nameRequireOrNot255Char"));
        }

        if (!DataUtil.isNullOrEmpty(dto.getSexStr())) {
            if (sexMale.equalsIgnoreCase(dto.getSexStr())) {
                dto.setSex(Constants.SEX.MALE);
            } else if (sexFemale.equalsIgnoreCase(dto.getSexStr()) || sexFemaleNoSign.equalsIgnoreCase(dto.getSexStr())) {
                dto.setSex(Constants.SEX.FEMALE);
            }
        }

        if (!DataUtil.isNullOrEmpty(dto.getCode()) && dto.getCode().length() > 50) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.codeNotOverMaxLength") + ",");
        }
        if(!DataUtil.isNullOrEmpty(dto.getCode()) && !Pattern.matches("[0-9]*", dto.getCode())) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.codeMustBe0To9") + ",");
        }

        if (!DataUtil.isNullOrEmpty(dto.getAddress()) && dto.getAddress().length() > 200) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.AddressNotOverMaxLength") + ",");
        }

        if (!DataUtil.isNullOrEmpty(dto.getNote()) && dto.getNote().length() > 200) {
            dto.setError(dto.getError() + Translator.toLocale("contact.error.NoteNotOverMaxLength") + ",");
        }

        if (!DataUtil.isNullOrEmpty(dto.getBirthdayStr())) {
            try {
                if (!DataUtil.isNullOrEmpty(dto.getBirthdayStr()) && dto.getBirthdayStr().length() > 10) {
                    dto.setError(dto.getError() + Translator.toLocale("contact.error.birthdayInvalid") + ",");
                } else {
                    sdf.setLenient(false);
                    dto.setBirthday(sdf.parse(dto.getBirthdayStr()).toInstant());
                }
            } catch (Exception ex) {
                logger.error(ex.getMessage(), ex);
                dto.setError(dto.getError() + Translator.toLocale("contact.error.birthdayInvalid") + ",");
            }
        }
        if (!DataUtil.isNullOrEmpty(dto.getError())) {
            dto.setError(dto.getError().substring(0, dto.getError().length() - 1));
        }
    }
}
