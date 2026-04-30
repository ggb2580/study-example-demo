package com.hrbu.esdemo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.hrbu.esdemo.entity.Hotel;
import com.hrbu.esdemo.mapper.HotelMapper;
import com.hrbu.esdemo.service.IHotelService;
import org.springframework.stereotype.Service;

/**
 * @author Say my name
 */
@Service
public class HotelService extends ServiceImpl<HotelMapper, Hotel> implements IHotelService {
}