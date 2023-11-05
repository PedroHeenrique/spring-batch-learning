package br.com.learn.batch.domain;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
public class Registro {
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				private Integer id;

				@Column(nullable = false)
				private String name;

				@OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
				@JoinColumn(name = "autor_id", unique = true)
				private Autor autor;

				public Registro() {
				}

				public void setAutor(Autor autor) {
								this.autor = autor;
				}

				@Override
				public String toString(){
								return "[ID-"  +id +" name- "+name+"]";
				}
}
