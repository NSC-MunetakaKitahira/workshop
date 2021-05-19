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
    "match/onematch?rounds=300"
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
  let $player1Gains = $player1.find(".gains").empty();
  let $player2Gains = $player2.find(".gains").empty();
  let $player1Hands = $("#round-results .player1").empty();
  let $player2Hands = $("#round-results .player2").empty();
  let $drawHands = $("#round-results .draw").empty();

  let $roundCount = $("#roundCount");

  let rc = 0;
  let player1Score = 0;
  let player2Score = 0;
  function processRound() {

    let round = match.rounds[rc];
    if (round === undefined) {
      return;
    }

    let result = round.result;

    $roundCount.text(rc + 1);

    player1Score += result.player1Gain;
    player2Score += result.player2Gain;
    $player1Score.text(player1Score);
    $player2Score.text(player2Score);

    if (result.player1Gain > result.player2Gain) {
      createGainBlock(result.player1Hand).appendTo($player1Gains);
      createHandBlock(result.player1Hand, "win").appendTo($player1Hands);
      createHandBlock(result.player2Hand, "lose").appendTo($player2Hands);
      createHandBlock("", "").appendTo($drawHands);
    }
    else if (result.player2Gain > result.player1Gain) {
      createGainBlock(result.player2Hand).appendTo($player2Gains);
      createHandBlock(result.player1Hand, "lose").appendTo($player1Hands);
      createHandBlock(result.player2Hand, "win").appendTo($player2Hands);
      createHandBlock("", "").appendTo($drawHands);
    }
    else {
      createGainBlock("draw").appendTo($player1Gains);
      createGainBlock("draw").appendTo($player2Gains);
      createHandBlock(result.player1Hand, "").appendTo($player1Hands);
      createHandBlock(result.player2Hand, "").appendTo($player2Hands);
      createHandBlock(result.player1Hand, "draw").appendTo($drawHands);
    }

    rc++;
    timer = setTimeout(processRound, 50);
  }
  processRound();
}

function createGainBlock(hand) {
  return $("<div>")
    .addClass("hand")
    .addClass(hand.toLowerCase())
    .text("_")
}

function createHandBlock(hand, result) {
  return $("<div>")
    .addClass("hand")
    .addClass(hand.toLowerCase())
    .addClass(result.toLowerCase())
  .text("_")
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