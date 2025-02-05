package model

import model.ChessPieces.{Cannon, Chariot, ChessPiece, Elephant, General, Guard, Horse, Soldier}
import scalafx.scene.layout.AnchorPane
import utils.Teams.{blackTeam, redTeam}
import utils.Maths._

/*
ChessBoard is an internal representation of the chessboard
Handles which player's turn
Validates a correct move
Creates jump amount etc.
Handles captures, attacks, checkmates
 */

object ChessBoard {

  // Alternative representation
  // val boardx = Array.fill(10,9)("O") // create board with 10 rows, 9 cols and fill it with O to indicate empty
//  val board: Array[Array[String]] = Array(
//    Array("Rb", "Hb", "Eb", "Ab", "Gb", "Ab", "Eb", "Hb", "Rb"),
//    Array.fill(10)("O"),
//    Array("O", "Cb", "O", "O", "O", "O", "O", "O", "Cb", "O"),
//    Array("Sb", "O", "Sb", "O", "Sb", "O", "Sb", "O", "Sb", "O"),
//    Array.fill(10)("O"),
//    Array.fill(10)("O"),
//    Array("Rr", "Hr", "Er", "Ar", "Gr", "Ar", "Er", "Hr", "Rr"),
//    Array.fill(10)("O"),
//    Array("O", "Cr", "O", "O", "O", "O", "O", "O", "Cr", "O"),
//    Array("Sr", "O", "Sr", "O", "Sr", "O", "Sr", "O", "Sr", "O"),
//  )
  // board foreach { row => row foreach print; println }

  val playerOne: Player = new Player()
  val playerTwo: Player = new Player()
  var board: AnchorPane = new AnchorPane()

  // time limit settings
  var timeLimit: Double = 15 // default timeLimit

  def changeTimer(timeLimit: Double): Unit = {
    playerOne.timer.timeLimit(timeLimit)
    playerTwo.timer.timeLimit(timeLimit)
  }

  // ROW INDEX and COL INDEX so that we can track the chess piece position and delete, move it etc.
  var pieces: List[ChessPiece] = List() // use list as we will iterate through the list often to check valid moves, obstruction etc.
  pieces = pieces ++ List( // red team, down side
    new Soldier(xTopLeft + 0 * xJumpValue, yTopLeft + 6 * yJumpValue, redTeam, _rowIndex = 6, _colIndex = 0),
    new Soldier(xTopLeft + 2 * xJumpValue, yTopLeft + 6 * yJumpValue, redTeam, _rowIndex = 6, _colIndex = 2),
    new Soldier(xTopLeft + 4 * xJumpValue, yTopLeft + 6 * yJumpValue, redTeam, _rowIndex = 6, _colIndex = 4),
    new Soldier(xTopLeft + 6 * xJumpValue, yTopLeft + 6 * yJumpValue, redTeam, _rowIndex = 6, _colIndex = 6),
    new Soldier(xTopLeft + 8 * xJumpValue, yTopLeft + 6 * yJumpValue, redTeam, _rowIndex = 6, _colIndex = 8),
    new General(xTopLeft + 4 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 4),
    new Guard(xTopLeft + 3 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 3),
    new Guard(xTopLeft + 5 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 5),
    new Elephant(xTopLeft + 2 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 2),
    new Elephant(xTopLeft + 6 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 6),
    new Horse(xTopLeft + 1 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 1),
    new Horse(xTopLeft + 7 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 7),
    new Chariot(xTopLeft + 8 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 8),
    new Chariot(xTopLeft + 0 * xJumpValue, yTopLeft + 9 * yJumpValue, redTeam, _rowIndex = 9, _colIndex = 0),
    new Cannon(xTopLeft + 1 * xJumpValue, yTopLeft + 7 * yJumpValue, redTeam, _rowIndex = 7, _colIndex = 1),
    new Cannon(xTopLeft + 7 * xJumpValue, yTopLeft + 7 * yJumpValue, redTeam, _rowIndex = 7, _colIndex = 7),
  )

  pieces = pieces ++ List( // black team, up side
    new Soldier(xTopLeft + 0 * xJumpValue, yTopLeft + 3 * yJumpValue, blackTeam, _rowIndex = 3, _colIndex = 0),
    new Soldier(xTopLeft + 2 * xJumpValue, yTopLeft + 3 * yJumpValue, blackTeam, _rowIndex = 3, _colIndex = 2),
    new Soldier(xTopLeft + 4 * xJumpValue, yTopLeft + 3 * yJumpValue, blackTeam, _rowIndex = 3, _colIndex = 4),
    new Soldier(xTopLeft + 6 * xJumpValue, yTopLeft + 3 * yJumpValue, blackTeam, _rowIndex = 3, _colIndex = 6),
    new Soldier(xTopLeft + 8 * xJumpValue, yTopLeft + 3 * yJumpValue, blackTeam, _rowIndex = 3, _colIndex = 8),
    new General(xTopLeft + 4 * xJumpValue, yTopLeft, blackTeam, text = "将", _rowIndex = 0, _colIndex = 4),
    new Guard(xTopLeft + 3 * xJumpValue, yTopLeft, blackTeam, text = "士", _rowIndex = 0, _colIndex = 3),
    new Guard(xTopLeft + 5 * xJumpValue, yTopLeft, blackTeam, text = "士", _rowIndex = 0, _colIndex = 5),
    new Elephant(xTopLeft + 2 * xJumpValue, yTopLeft, blackTeam, _rowIndex = 0, _colIndex = 2),
    new Elephant(xTopLeft + 6 * xJumpValue, yTopLeft, blackTeam, _rowIndex = 0, _colIndex = 6),
    new Horse(xTopLeft + 1 * xJumpValue, yTopLeft, blackTeam, text = "馬", _rowIndex = 0, _colIndex = 1),
    new Horse(xTopLeft + 7 * xJumpValue, yTopLeft, blackTeam, text = "馬", _rowIndex = 0, _colIndex = 7),
    new Chariot(xTopLeft + 0 * xJumpValue, yTopLeft, blackTeam, text = "車", _rowIndex = 0, _colIndex = 0),
    new Chariot(xTopLeft + 8 * xJumpValue, yTopLeft, blackTeam, text = "車", _rowIndex = 0, _colIndex = 8),
    new Cannon(xTopLeft + 1 * xJumpValue, yTopLeft + 2 * yJumpValue, blackTeam, text = "砲", _rowIndex = 2, _colIndex = 1),
    new Cannon(xTopLeft + 7 * xJumpValue, yTopLeft + 2 * yJumpValue, blackTeam, text = "砲", _rowIndex = 2, _colIndex = 7),
  )

  private var _player1Turn: Boolean = true // starts with player 1, if false, means player 2's turn
  def player1Turn: Boolean = _player1Turn

  // checks if a move to a row and col is valid (no piece of same team)
//  def validMove(row: Int, col: Int, currentTeam: Team): Boolean = {
//    val pieceOnSquare = board(row)(col)
//    var pieceTeam: Team = redTeam // temporarily setting
//
//    if (pieceOnSquare != "O") { // there is a piece on this square
//      pieceOnSquare.charAt(1) match {
//        case 'r' => pieceTeam = Teams.redTeam
//        case 'b' => pieceTeam = Teams.blackTeam
//        case _ => println("Error, piece does not have team!")
//      }
//      if (pieceTeam == currentTeam) false else true // if piece is same team, this is not a valid move
//
//    } else {
//      return false // out of bounds or board movement
//    }
//  }

  // returns whether a piece has been successfully deleted, if false, means no piece there
  def deletePiece(colIndex: Int, rowIndex: Int): Unit = {
    pieces = pieces.filterNot(chessPiece => (chessPiece.rowIndex == rowIndex && chessPiece.colIndex == colIndex))
    board.children = pieces
  }

  def getPiece(colIndex: Int, rowIndex: Int): Option[ChessPiece] = {
    val pieceList = pieces.filter(chessPiece => (chessPiece.rowIndex == rowIndex && chessPiece.colIndex == colIndex))
    if (pieceList.length == 0) {
      None
    } else {
      Option(pieceList.head)
    }
  }

  def isPiece(colIndex: Int, rowIndex: Int): Boolean = {
    val pieceList = pieces.filter(chessPiece => (chessPiece.rowIndex == rowIndex && chessPiece.colIndex == colIndex))
    if (pieceList.length == 0) false else true
  }

  // checks if there is an obstruction on the col and row index that the piece wants to move in
  // if a endCol or endRow index is supplied, it will scan the positions until that end coordinate
  // returns the obstruction piece row and column index
  def obstruction(colIndex: Int, rowIndex: Int, endColIndex: Option[Int] = None, endRowIndex: Option[Int] = None): (Int, Int) = {
    for (piece <- pieces) {
      // direct on spot obstruction
      println("piece.rowIndex " + piece.rowIndex + " piece.colIndex: " + colIndex )
      if (piece.rowIndex == rowIndex && piece.colIndex == colIndex) {
        return (rowIndex, colIndex)

      } else {
        return (0, 0)
      }
      // same col

      // same row

      // diagonal??
    }
    (0,0) // no match
  }

  // def checkRiver() = {}

  def switchTurn(): Unit = {
    _player1Turn = !_player1Turn

    if (playerOne.timer.running) {
      playerOne.timer.stopTimer()
      playerTwo.timer.startTimer()
      TimerPanel.updateTime(blackTeam)
    } else if (playerTwo.timer.running) {
      playerOne.timer.startTimer()
      playerTwo.timer.stopTimer()
      TimerPanel.updateTime(redTeam)
    }
  }


}
