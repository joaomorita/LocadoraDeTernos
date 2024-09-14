package br.com.locadoradeternos.api_rest_locadora.model;

import com.fasterxml.jackson.annotation.JsonSetter;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Determinando a estrutura lógica das entidades do banco de dados e como os dados podem ser organizados e manipulados.


@Entity /* Entity para estabelecer uma ligação da entidade com a tabela no banco de dados */
@Getter
@Setter
@NoArgsConstructor /* Gerando um construtor sem parametros */
public class Ternos {

    @Id /* define o id como uma primary key */
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* Os valores vão ser únicos, gerados automaticamentes */
    private Long id;

    @Pattern(regexp = "^(P|M|G|GG)$", message = "Tamanho informado é inválido: P, M, G, GG")
    @NotBlank(message = "É obrigatório informar o tamanho do terno: P, M, G, GG")
    private String tamanho;

    @NotBlank(message = "Informe a cor do terno")
    private String cor;

    @NotNull(message = "Informe se o terno está disponível: true ou false")
    private Boolean disponivel;

}
