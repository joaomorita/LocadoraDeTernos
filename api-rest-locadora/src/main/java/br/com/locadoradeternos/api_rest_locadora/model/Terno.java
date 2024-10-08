package br.com.locadoradeternos.api_rest_locadora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

// Determinando a estrutura lógica das entidades do banco de dados e como os dados podem ser organizados e manipulados.


@Entity /* Entity para estabelecer uma ligação da entidade com a tabela no banco de dados */
@Getter // Gera os métodos GET
@Setter // Gera os métodos SET
@NoArgsConstructor /* Gerando um construtor sem parametros */
public class Terno {

    @Id /* define o id como uma primary key */
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* Os valores vão ser únicos, gerados automaticamentes */
    private Long id;

    // O campo tamanho deve corresponder a P, M, G, GG
    @Pattern(regexp = "^(P|M|G|GG)$", message = "Tamanho informado é inválido: P, M, G, GG")
    private String tamanho;

    // Não deixa o campo cor ser uma string em branco
    @NotBlank(message = "Informe a cor do terno")
    private String cor;

    // Não deixa o campo disponivel ser nulo
    @NotNull(message = "Informe se o terno está disponível: true ou false")
    private Boolean disponivel;

}
