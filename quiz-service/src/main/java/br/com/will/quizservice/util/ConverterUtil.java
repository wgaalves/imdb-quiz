package br.com.will.quizservice.util;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ConverterUtil {

    private final ModelMapper modelMapper = new ModelMapper();

    public <D> D convertTo(Object bean, Class<D> dto) {
        return modelMapper.map(bean, dto);
    }

    public <S, T> List<T> mapList(List<S> source, Class<T> targetClass) {
        return source
                .stream()
                .map(element -> modelMapper.map(element, targetClass))
                .collect(Collectors.toList());
    }
}
