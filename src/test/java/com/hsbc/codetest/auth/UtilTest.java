package com.hsbc.codetest.auth;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.hsbc.codetest.auth.domain.Role;
import com.hsbc.codetest.auth.exception.AlreadyExistException;
import com.hsbc.codetest.auth.utils.TokenUtil;
import org.junit.jupiter.api.Test;

import javax.swing.text.StyleContext;

import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

public class UtilTest {

    private static Gson gson = new GsonBuilder().create();

    static class Sample{
        String name;
        String others;
        long number;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Sample sample = (Sample) o;
            return number == sample.number && Objects.equals(name, sample.name) && Objects.equals(others, sample.others);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, others, number);
        }
    }

    @Test
    public void testEnDeCrypt() throws AlreadyExistException {
        Sample s = new Sample();
        s.name = "abac";
        s.number = 23131231L;
        s.others = null;
        String json = gson.toJson(s);
        String secret = TokenUtil.encrypt(json);
        String plainText = TokenUtil.decrypt(secret);
        assertThat(json).isEqualTo(plainText);

        Sample s2 = gson.fromJson(plainText,Sample.class);
        assertThat(s).isEqualTo(s2);
    }
}
