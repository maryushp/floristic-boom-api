package com.floristicboom.bonus.service;

import com.floristicboom.bonus.model.Bonus;
import com.floristicboom.bonus.model.BonusDTO;
import com.floristicboom.bonus.repository.BonusRepository;
import com.floristicboom.utils.exceptionhandler.exceptions.ItemAlreadyExistsException;
import com.floristicboom.utils.exceptionhandler.exceptions.NoSuchItemException;
import com.floristicboom.utils.mappers.EntityToDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.floristicboom.utils.Constants.*;
import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

@Service
@RequiredArgsConstructor
public class DefaultBonusService implements BonusService {
    private final BonusRepository bonusRepository;
    private final EntityToDtoMapper entityToDtoMapper;

    @Override
    public BonusDTO create(BonusDTO bonusDTO) {
        Bonus bonus = entityToDtoMapper.toBonus(bonusDTO);
        if (ifBonusExist(bonus))
            throw new ItemAlreadyExistsException(String.format(BONUS_ALREADY_EXISTS, bonus.getPromoCode()));
        return entityToDtoMapper.toBonusDTO(bonusRepository.save(bonus));
    }

    @Override
    public Page<BonusDTO> readAll(Pageable pageable) {
        return bonusRepository.findAll(pageable).map(entityToDtoMapper::toBonusDTO);
    }

    @Override
    public BonusDTO findById(Long id) {
        return bonusRepository.findById(id).map(entityToDtoMapper::toBonusDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(BONUS_NOT_FOUND_ID, id)));
    }

    @Override
    public BonusDTO findByPromoCode(String promoCode) {
        return bonusRepository.findByPromoCode(promoCode).map(entityToDtoMapper::toBonusDTO)
                .orElseThrow(() -> new NoSuchItemException(String.format(BONUS_NOT_FOUND_PROMO_CODE, promoCode)));
    }

    @Override
    public void delete(Long id) {
        bonusRepository.findById(id).ifPresentOrElse(bonusRepository::delete,
                () -> {
                    throw new NoSuchItemException(String.format(BONUS_NOT_FOUND_ID, id));
                });
    }

    private boolean ifBonusExist(Bonus bonus) {
        ExampleMatcher bonusMatcher = ExampleMatcher.matching()
                .withIgnorePaths(DISCOUNT, DURATION_DATE)
                .withMatcher(PROMO_CODE, exact());
        return bonusRepository.exists(Example.of(bonus, bonusMatcher));
    }
}