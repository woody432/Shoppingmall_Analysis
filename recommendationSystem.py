import sys
import numpy as np
import pandas as pd
from sklearn.metrics.pairwise import cosine_similarity

#데이터
data = pd.DataFrame({ 'gender':[0, 1, 0, 1, 0, 1],
                      'height':[171, 158, 178, 160, 180, 162],
                      'weight':[68, 45, 74, 50, 80, 55]
                    })

# new_data = np.array([1, 163, 53])
new_data = list(map(float, sys.argv[1:]))

# #데이터 들어오면 similarity 계산
similarities = cosine_similarity(data, [new_data])

product_num = pd.Series(['5004513586', '5004513586', '5004513587', '5004513587', '5004513588', '5004513588'])

# (품번, 값) 형태의 튜플 생성
clothes_similarity_tuples = list(zip(product_num, similarities.flatten()))

# 유사도가 높은 순서로 정렬
sorted_similarities = sorted(clothes_similarity_tuples, key=lambda x: x[1], reverse=True)

for item_number, similarity in sorted_similarities:
#     print(f"{item_number}: {similarity}")
    print(f"{item_number}")