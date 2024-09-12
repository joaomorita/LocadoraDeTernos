package br.com.locadoradeternos.api_rest_locadora.model;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

// Determinando a estrutura lógica das entidades do banco de dados e como os dados podem ser organizados e manipulados.


@Entity /* Entity para estabelecer uma ligação da entidade com a tabela no banco de dados */
@Data /* Para substituir a necessidade dos geters e setters */
@NoArgsConstructor /* Gerando um construtor sem parametros */
public class Ternos {

    @Id /* define o id como uma primary key */
    @GeneratedValue(strategy = GenerationType.IDENTITY) /* Os valores vão ser únicos, gerados automaticamentes */
    private Long id;

    @Enumerated(EnumType.STRING)
    private Tamanho tamanho;
    
    private String cor;
    private Boolean disponivel;
}
