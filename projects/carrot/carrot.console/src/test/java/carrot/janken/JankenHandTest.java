package carrot.janken;

import static org.junit.Assert.*;
import static carrot.judge.JankenHand.*;
import static org.hamcrest.CoreMatchers.*;

import org.junit.jupiter.api.Test;

class JankenHandTest {

	@Test
	void handToWin() {
		assertThat(GU.handToWin(), is(PA));
		assertThat(CHOKI.handToWin(), is(GU));
		assertThat(PA.handToWin(), is(CHOKI));
	}

	@Test
	void handToLose() {
		assertThat(GU.handToLose(), is(CHOKI));
		assertThat(CHOKI.handToLose(), is(PA));
		assertThat(PA.handToLose(), is(GU));
	}

}
