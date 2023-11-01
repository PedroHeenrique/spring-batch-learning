package br.com.learn.batch.release.domain;


import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "result_process")
public class ResultProcess {
				@Id
				@GeneratedValue(strategy = GenerationType.IDENTITY)
				private int id;

				@Column(name = "result")
				private String result;

				@Override
				public String toString(){
								return "result= " + result;
				}
}