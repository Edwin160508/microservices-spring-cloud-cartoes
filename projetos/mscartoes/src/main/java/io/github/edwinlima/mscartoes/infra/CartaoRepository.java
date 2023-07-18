package io.github.edwinlima.mscartoes.infra;

import io.github.edwinlima.mscartoes.application.representation.CartaoRequest;
import io.github.edwinlima.mscartoes.application.representation.CartaoResponse;
import io.github.edwinlima.mscartoes.domain.Cartao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface CartaoRepository extends JpaRepository<Cartao, Long> {

    List<Cartao> findByRendaLessThanEqual(BigDecimal renda);
}
