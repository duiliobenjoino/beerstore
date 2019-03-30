package com.dbmatos.beerstore.error;

import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import com.dbmatos.beerstore.error.ErrorResponse.ApiError;

public abstract class AbstractErrorHandler {
	
	protected static final Logger LOG = LoggerFactory.getLogger(ApiExceptionHandler.class);
	protected static final String NO_MESSSAGE_AVAILABLE = "Mensagem não disponível";
	
	@Autowired
	protected MessageSource apiErrorMessageSource;
	
	protected ApiError toApiError(String code, Locale locale, Object... args) {
        String message;
        try {
            message = apiErrorMessageSource.getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            LOG.error("Could not find any message for {} code under {} locale", code, locale);
            message = NO_MESSSAGE_AVAILABLE;
        }

        return new ApiError(code, message);
    }


}
