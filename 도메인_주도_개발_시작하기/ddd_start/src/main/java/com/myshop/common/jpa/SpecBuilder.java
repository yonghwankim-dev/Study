package com.myshop.common.jpa;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public class SpecBuilder {
	public static <T> Builder<T> builder(Class<T> type) {
		return new Builder<>();
	}

	public static class Builder<T> {
		private List<Specification<T>> specs = new ArrayList<>();

		public Builder<T> and(Specification<T> spec) {
			specs.add(spec);
			return this;
		}

		public Builder<T> ifHasText(String value, Function<String, Specification<T>> specSupplier) {
			if (StringUtils.hasText(value)) {
				specs.add(specSupplier.apply(value));
			}
			return this;
		}

		public Builder<T> ifTrue(Boolean cond, Supplier<Specification<T>> specSupplier) {
			if (cond != null && cond) {
				specs.add(specSupplier.get());
			}
			return this;
		}

		public Specification<T> toSpec() {
			Specification<T> spec = Specification.where(null);
			for (Specification<T> s : specs) {
				spec = spec.and(s);
			}
			return spec;
		}
	}
}
