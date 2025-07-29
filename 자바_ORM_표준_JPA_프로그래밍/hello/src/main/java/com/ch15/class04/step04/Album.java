package com.ch15.class04.step04;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;


@Entity
@DiscriminatorValue("A")
public class Album extends Item {
}
