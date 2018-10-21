/*
 * Copyright 2018 Griffin DeJohn
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */

package io.github.gdejohn.procrastination;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.github.gdejohn.procrastination.Functions.apply;
import static io.github.gdejohn.procrastination.Trampoline.call;
import static io.github.gdejohn.procrastination.Trampoline.terminate;
import static org.assertj.core.api.Assertions.assertThat;

class TrampolineTest {
    @Test
    void sequenceNthElementRecursive() {
        Optional<Integer> value = Trampoline.evaluate(
            Sequences.ints(), 100_000, f -> sequence -> index -> sequence.match(
                (head, tail) -> index == 0 ? terminate(Optional.of(head)) : call(() -> apply(f, tail, index - 1)),
                () -> terminate(Optional.empty())
            )
        );
        assertThat(value).hasValue(100_000);
    }

    @Test
    void factorial() {
        int result = Trampoline.evaluate(
            6, 1, factorial -> n -> m -> n == 0 ? terminate(m) : call(factorial, n - 1, n * m)
        );
        assertThat(result).isEqualTo(720);
    }
}
