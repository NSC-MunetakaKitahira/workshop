init();

let $selectPlayer1 = $("#select-player1");
let $selectPlayer2 = $("#select-player2");

async function init() {
  
  let playerNames = await fetchJson("players/names");
  playerNames.forEach(name => {
    $selectPlayer1.append($("<option>").attr("value", name).text(name));
    $selectPlayer2.append($("<option>").attr("value", name).text(name));
  });

  $("#start-match").on("click", () => {
    startMatch();
  });

}

let timer = null;
async function startMatch() {

  clearTimeout(timer);

  let match = await fetchJson(
    "match/onematch?rounds=3000"
    + "&player1="
    + $selectPlayer1.val()
    + "&player2="
    + $selectPlayer2.val())

  let $player1 = $("#player1");
  let $player2 = $("#player2");
  $player1.find(".player-name").text(match.player1);
  $player2.find(".player-name").text(match.player2);
  
  let $player1Score = $player1.find(".score").empty();
  let $player2Score = $player2.find(".score").empty();

  let $roundCount = $("#roundCount");

  let rc = 0;
  let player1Score = 0;
  let player2Score = 0;

  let $view = $("#view");
  let viewCanvas = $("#view canvas")[0];
  let ctx = viewCanvas.getContext("2d");
  
  ctx.clearRect(0, 0, viewCanvas.width, viewCanvas.height);

  function viewRoundHand(player, round, hand) {
    let w = 2;
    let h = 8;
    let y1 = 167;
    let y2 = y1 + h * 4;
    let yDraw = y1 + h * 2;

    let x = w * round;
    let y = (player === "draw") ? yDraw : ((player === 1) ? y1 : y2);
    
    let color = {
      win: {
        gu: "#f55",
        choki: "#ee2",
        pa: "#66f",
      },
      draw: {
        gu: "#822",
        choki: "#661",
        pa: "#228",
      }
    }[player === "draw" ? "draw": "win"][hand.toLowerCase()];

    ctx.fillStyle = color;
    ctx.fillRect(x, y, w, h);
  }

  function viewGains(player, currentScore, gain) {

    let h = 40;
    let y1 = 85;
    let y2 = y1 + 163;

    let w = gain;
    let x = currentScore;
    let y = (player === 1) ? y1 : y2;

    let color = {
      1: "#444",
      2: "#f55",
      4: "#ee2",
      5: "#66f",
    }[gain];
    
    ctx.fillStyle = color;
    ctx.fillRect(x, y, w, h);
  }
  

  function processRound() {
    let round = match.rounds[rc];
    if (round === undefined) {
      return;
    }

    let result = round.result;

    $roundCount.text(rc + 1);

    viewGains(1, player1Score, result.player1Gain);
    viewGains(2, player2Score, result.player2Gain);

    if (result.player1Gain > result.player2Gain) {
      viewRoundHand(1, rc, result.player1Hand);
    }
    else if (result.player2Gain > result.player1Gain) {
      viewRoundHand(2, rc, result.player2Hand);
    }
    else {
      viewRoundHand("draw", rc, result.player1Hand);
    }

    player1Score += result.player1Gain;
    player2Score += result.player2Gain;
    $player1Score.text(player1Score);
    $player2Score.text(player2Score);

    $view.scrollLeft(Math.max(player1Score, player2Score) - ($view.width() - 300));

    rc++;
    
    if (rc % 2 === 0) {
      timer = setTimeout(processRound, 5);
    } else {
    	processRound();
    }
  }
  
  processRound();
}

function getSelectedLevel() {
  let lc = $selectLevel.val();
  return jankenResult.levels.find(l => l.levelCount == lc);
}

function getSelectedMatch() {
  let level = getSelectedLevel();
  let mc = $selectMatch.val();
  return level.matches.find(m => m.matchCount == mc);
}

async function fetchJson(path) {
  let fetched = await fetch("../webapi/" + path);
  return await fetched.json();
}