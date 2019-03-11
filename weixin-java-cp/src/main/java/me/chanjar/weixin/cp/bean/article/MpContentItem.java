package me.chanjar.weixin.cp.bean.article;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * <pre>
 *  Created by BinaryWang on 2017/3/27.
 * </pre>
 *
 * @author Binary Wang
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MpContentItem implements Serializable {
  private static final long serialVersionUID = -9155804987554947349L;

  private String key;
  private String value;
}
