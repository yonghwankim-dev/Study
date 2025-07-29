"""
title : 베스트 앨범
date : 2020-09-10
author : 김용환
reference : https://programmers.co.kr/learn/courses/30/lessons/42579
"""


def sum_plays(idxs, plays):
    result = 0
    for i in idxs:
        result += plays[i]
    return result


albums = {}


genres = ["classic", "pop", "classic", "classic", "pop"]
plays = [500, 600, 150, 800, 2500]

for genre in set(genres):
    albums.update({genre: []})

for genre, play in zip(genres, plays):
    albums[genre].append(plays.index(play))

# 속한 노래가 많이 재생된 장르를 구한다. key: "classic" value: 1450
total_plays = {}
for k, v in albums.items():
    total_plays[k] = sum_plays(v, plays)

print(total_plays)
