def sort(array)
	Integer i = 0
	Integer j = 0
	while i < array.length() - 1
		j = 0
		while j < array.length() - i - 1
			if array.compare(j , j + 1) then
				array.exchange(j + 1 , j)
			end
			j = j + 1
		end
		i = i + 1
	end
end