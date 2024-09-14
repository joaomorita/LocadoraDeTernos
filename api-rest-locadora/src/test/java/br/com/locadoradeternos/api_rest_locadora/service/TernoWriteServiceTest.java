package br.com.locadoradeternos.api_rest_locadora.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import br.com.locadoradeternos.api_rest_locadora.model.Terno;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

public class TernoWriteServiceTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidTerno() {
        Terno terno = new Terno();
        terno.setTamanho("M");
        terno.setCor("Azul");
        terno.setDisponivel(true);

        Set<ConstraintViolation<Terno>> violations = validator.validate(terno);
        assertThat(violations).isEmpty();  // Espera-se que não haja violações
    }

    @Test
    public void testInvalidTamanhoTerno() {
        Terno terno = new Terno();
        terno.setTamanho("XXL");  // Tamanho inválido
        terno.setCor("Preto");
        terno.setDisponivel(true);

        Set<ConstraintViolation<Terno>> violations = validator.validate(terno);
        assertThat(violations).isNotEmpty();  // Espera-se violações
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Tamanho informado é inválido: P, M, G, GG"));
    }

    @Test
    public void testBlankTamanhoTerno() {
        Terno terno = new Terno();
        terno.setTamanho("");  // Tamanho em branco
        terno.setCor("Branco");
        terno.setDisponivel(true);

        Set<ConstraintViolation<Terno>> violations = validator.validate(terno);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Tamanho informado é inválido: P, M, G, GG"));
    }

    @Test
    public void testCorObrigatoria() {
        Terno terno = new Terno();
        terno.setTamanho("G");
        terno.setCor("");  // Cor em branco
        terno.setDisponivel(true);

        Set<ConstraintViolation<Terno>> violations = validator.validate(terno);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Informe a cor do terno"));
    }

    @Test
    public void testDisponivelObrigatorio() {
        Terno terno = new Terno();
        terno.setTamanho("P");
        terno.setCor("Cinza");
        terno.setDisponivel(null);  // Disponibilidade nula

        Set<ConstraintViolation<Terno>> violations = validator.validate(terno);
        assertThat(violations).isNotEmpty();
        assertThat(violations).anyMatch(v -> v.getMessage().contains("Informe se o terno está disponível: true ou false"));
    }
}
