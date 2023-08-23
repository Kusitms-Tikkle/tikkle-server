package com.kusitms.tikkle.sticker;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StickerRepository extends JpaRepository<Sticker, Long> {
    Optional<Sticker> findByAccountIdAndMemoIdAndDtype(Long accountId, Long memoId, String dtype);
}
