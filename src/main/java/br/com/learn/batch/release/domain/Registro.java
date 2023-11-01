package br.com.learn.batch.release.domain;


import lombok.Getter;

import jakarta.persistence.*;

@Entity
@Getter
public class Registro {
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				private Integer id;

				@Column(nullable = false)
				private String name;

				@Override
				public String toString(){
								return "[ID-"  +id +" name- "+name+"]";
				}
}
