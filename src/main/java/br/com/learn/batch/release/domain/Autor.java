package br.com.learn.batch.release.domain;


import lombok.*;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Autor{
				@Id
				public int id;
				public String title;
				public String autor;
				public int idade;

				@Override
				public String toString() {
								return "Autor{" + "id=" + id + ", title='" + title + '\'' + ", autor='" + autor + '\'' + ", idade=" + idade + '}';
				}
}