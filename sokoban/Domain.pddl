;Header and description

(define (domain sokoban)

;remove requirements that are not needed
(:requirements :strips :typing)

(:types position direction
)

; un-comment following line if constants are needed
;(:constants )

(:predicates 
            ;block is in pos (position)
            (block-in ?pos - position)
            ;player is pos (position)
            (player-in ?pos - position)
            ;pos (position) is empty (can be moved to)
            (empty ?pos - position)
            ;pos1 is adjacent to pos2, in the direction dir (moving from pos1 to pos1 is possible) 
            (adjacent ?pos1 ?pos2 - position ?dir - direction)
)

;define actions here

(:action go 
    :parameters (?pos1 ?pos2 - position ?dir - direction)
    ;destination is empty, player is in position, destination is adjacent to position
    :precondition (and (empty ?pos2) (player-in ?pos1) (adjacent ?pos1 ?pos2 ?dir))
    ;pos2 is occupied, player is no longer in pos1, player is in pos2, pos2 is now occupied 
    :effect (and (not (empty ?pos2)) (not (player-in ?pos1)) (player-in ?pos2) (empty ?pos1))
)

(:action bump
    :parameters (?pos1 ?pos2 ?pos3 - position ?dir - direction)
    ;destination(pos2) is empty, player is in current position(pos1), destination(pos2) is adjacent to current position(pos1)
    :precondition (and (empty ?pos3) (player-in ?pos1) (adjacent ?pos1 ?pos2 ?dir) (adjacent ?pos2 ?pos3 ?dir) (block-in ?pos2))
    ;destination(pos1) is occupied, pos3 (where the block is bumped) is occupied, player is no longer in pos1, player in pos2, block in pos3
    :effect (and (not (empty ?pos2)) (not (empty ?pos3)) (not (player-in ?pos1)) (player-in ?pos2) (block-in ?pos3))
)

)
