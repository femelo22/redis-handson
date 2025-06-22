package br.com.lfmelo.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity(name = "TBL_LIVRO")
@Table(name = "TBL_LIVRO")
public class Livro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String autor;
}
